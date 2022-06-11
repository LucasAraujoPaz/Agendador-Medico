import { index } from "./index.js";

function start() {
    const loginForm = /** @type {HTMLFormElement} */
        (document.querySelector("#login form"));

    loginForm.onsubmit = async (event) => {
        event.preventDefault();
        const email = /** @type {HTMLInputElement} */
            (loginForm.elements.namedItem("email")).value;
        const password = /** @type {HTMLInputElement} */
            (loginForm.elements.namedItem("password")).value;

        const response = await fetch("/login", {
            headers: { "Content-Type": "application/json" },
            method: "POST",
            body: JSON.stringify({ email, password })
        });
        try {
            if ( ! response.ok)
                throw await response.json();
        } catch (/** @type {any} */ reason) {
            return alert(reason?.message ?? "Invalid E-mail/Password.");
        }

        const jwt = await response.text();
        document.cookie = `Authorization=Bearer ${jwt}`;
        index.main.innerHTML = "";
    }
}

const login = { start }
export { login };