import { index } from "./index.js";

function start() {
    const registerForm = /** @type {HTMLFormElement} */
        (document.querySelector("#register form"));

    registerForm.onsubmit = async (event) => {
        event.preventDefault();
        const email = /** @type {HTMLInputElement} */
            (registerForm.elements.namedItem("email")).value;
        const password = /** @type {HTMLInputElement} */
            (registerForm.elements.namedItem("password")).value;
        const repeatedPassword = /** @type {HTMLInputElement} */
            (registerForm.elements.namedItem("confirm-password")).value;
        
        if (password !== repeatedPassword)
            return alert("Passwords must be equal.");

        try {
            await doRegister({ email, password });
        } catch (/** @type {any} */ reason) {
            return alert(reason?.message ?? "Invalid E-mail/Password.");
        }

        index.main.innerHTML = "";
    }
}

/** @param {import("./login.js").LoginDTO} loginDTO */
async function doRegister(loginDTO) {
    const response = await fetch("/users", {
        headers: { "Content-Type": "application/json" },
        method: "POST",
        body: JSON.stringify(loginDTO)
    });

    if (!response.ok)
        throw await response.json();
}

const register = { start };
export { register };