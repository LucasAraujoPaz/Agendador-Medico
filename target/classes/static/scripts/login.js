import { router } from "./router.js";

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

        router.navigate("#/");
    }
}

/** @typedef {{email: string, password: string}} LoginDTO */

/** @param {LoginDTO} loginDTO */
async function doLogin(loginDTO) {
    const response = await fetch("/api/login", {
        headers: { "Content-Type": "application/json" },
        method: "POST",
        body: JSON.stringify(loginDTO)
    });

    if (!response.ok)
        throw await response.json();

    const jwt = await response.text();
    document.cookie = `Authorization=Bearer_${jwt}; max-age=3600;`;
}

function doLogout() {
    document.cookie = "Authorization=; path=/; max-age=-1;";
}

function userIsLoggedIn() {
    return document.cookie.includes("Authorization=Bearer");
}

const login = { start, doLogout, userIsLoggedIn };
export { login };