import axios, { AxiosError } from 'axios';
import React, { FormEvent, useEffect, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

const Redirect = ({ signin, setSignin }: { signin: boolean, setSignin: React.Dispatch<React.SetStateAction<boolean>> }) => {

    const [params, setParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        const code = params.get('code');
        const verifier = localStorage.getItem('verifier');
        if (code != null && verifier != null) {
            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
            myHeaders.append("Authorization", "Basic dG9kbzpzZWNyZXQ=");

            const urlencoded = new URLSearchParams();
            urlencoded.append("client_id", "todo");
            urlencoded.append("redirect_uri", "http://localhost:3000/redirect");
            urlencoded.append("grant_type", "authorization_code");
            urlencoded.append("code", code);
            urlencoded.append("code_verifier", verifier);

            axios.post(`${process.env.REACT_APP_BACKEND}/oauth2/token`, urlencoded, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    Authorization: "Basic dG9kbzpzZWNyZXQ="
                }
            }).then(response => {
                const { access_token, refresh_token, expires_in } = response.data
                localStorage.setItem('access_token', access_token);
                localStorage.setItem('refresh_token', refresh_token);
                localStorage.setItem('expires_in', Math.floor(Date.now() / 1000) + expires_in);
                setSignin(true)
                navigate('/');
            }).catch((err: AxiosError) => {
                if (err.response && err.response.data)
                    console.log(err.response.data)
                else if (err.response) console.log(err.response)
                else console.log(err)
            })
        } else {
            alert("Error: code or verifier not found")
        }
    }, [])

    return (
        <div>
            <h3>Redirecting ...</h3>
        </div>
    );
};

export default Redirect;
