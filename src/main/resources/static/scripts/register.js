import { login } from "./login.js";
import { router } from "./router.js";

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

        router.navigate("#/login");
    }
}

/** @param {import("./login.js").LoginDTO} loginDTO */
async function doRegister(loginDTO) {
    const response = await fetch("/api/users", {
        headers: { "Content-Type": "application/json" },
        method: "POST",
        body: JSON.stringify(loginDTO)
    });

    if (!response.ok)
        throw await response.json();
}

async function deleteAccount() {
    const response = await fetch("/api/users/me", {
        method: "DELETE"
    });
    if (!response.ok)
        throw await response.json();
}

async function onDeleteAccount() {
    if (!confirm("Confirm deletion of account?"))
        return;
    try {
        await deleteAccount();
        login.doLogout();
    } catch (/** @type {any} */ reason) {
        return alert(reason?.message ?? "Error.");
    }
    router.navigate("#/");
}

const register = { start, onDeleteAccount };
export { register };