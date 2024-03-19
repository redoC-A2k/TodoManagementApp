import { useEffect } from "react"

export default function () {
    useEffect(()=>{
        localStorage.clear()
        console.log("cookiee : "+document.cookie)
        window.location.href = process.env.REACT_APP_BACKEND+'/logout'    
    })
    return (
        <div>
            <h3>Redirecting to logout page...</h3>
        </div>
    )
}