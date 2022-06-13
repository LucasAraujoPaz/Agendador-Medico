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

        try {
            await doLogin({ email, password });
        } catch (/** @type {any} */ reason) {
            return alert(reason?.message ?? "Invalid E-mail/Password.");
        }

        index.main.innerHTML = "";
    }
}

/** @typedef {{email: string, password: string}} LoginDTO */

/** @param {LoginDTO} loginDTO */
async function doLogin(loginDTO) {
    const response = await fetch("/login", {
        headers: { "Content-Type": "application/json" },
        method: "POST",
        body: JSON.stringify(loginDTO)
    });

    if (!response.ok)
        throw await response.json();

    const jwt = await response.text();
    document.cookie = `Authorization=Bearer ${jwt}`;
}

function doLogout() {
    document.cookie = "Authorization=; path=/; max-age=-1;";
}

const login = { start, doLogout };
export { login };