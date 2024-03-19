import axios from "axios";

export async function getToken(): Promise<String | null> {
    if (localStorage.getItem('expires_in') && Number(localStorage.getItem('expires_in')) < Math.floor(Date.now() / 1000)) {
        const refresh_token = localStorage.getItem('refresh_token');

        const urlencoded = new URLSearchParams();
        urlencoded.append("grant_type", "refresh_token");
        urlencoded.append("refresh_token", refresh_token!);
        try {
            let response = await axios.post(`${process.env.REACT_APP_BACKEND}/oauth2/token`, urlencoded, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    Authorization: "Basic dG9kbzpzZWNyZXQ="
                }
            })
            const { access_token, expires_in } = response.data
            localStorage.setItem('access_token', access_token);
            localStorage.setItem('expires_in', Math.floor(Date.now() / 1000) + expires_in);
            return access_token;
        } catch (error) {
            localStorage.clear()
            redirect()
            return getToken()
        }
    } else {
        return localStorage.getItem('access_token');
    }
}


function base64URLEncode(str: String | Buffer) {
    return str.toString('base64')
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=/g, '');
}

async function sha256(str: string) {
    const encoder = new TextEncoder();
    const data = encoder.encode(str);
    console.log(str)
    const digest = await crypto.subtle.digest('SHA-256', data);
    let arr = new Uint8Array(digest);
    let hash = btoa(String.fromCharCode(...arr))
        .replace(/=/g, '').replace(/\+/g, '-').replace(/\//g, '_')
    console.log(hash)
    return hash;
}

export async function redirect() {
    let verifier = base64URLEncode("afshanahmedkhanfaizullahahmedkhanirshadahmedkhanshabinakhan");
    let hash = await sha256(verifier)

    let challenge = base64URLEncode(hash);
    // console.log("verifier = ",verifier,"challenge = ",challenge)
    localStorage.setItem('challenge', challenge);
    localStorage.setItem('verifier', verifier);
    window.location.href = (`${process.env.REACT_APP_BACKEND}/oauth2/authorize?client_id=todo&response_type=code&redirect_uri=http://localhost:3000/redirect&code_challenge_method=S256&code_challenge=${challenge}`)
}