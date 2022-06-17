import { router } from "./router.js";
import { register } from "./register.js";
import { login } from "./login.js";

const main = /** @type {HTMLElement} */
    (document.querySelector("main"));
const dialog = /** @type {HTMLElement} */
    (document.querySelector("#dialog"));

/** @param {{url: string, element?: HTMLElement }} _ */
const setInnerHTML = async ({ url, element = main }) => {
    const response = await fetch(url, { cache: "no-cache" });
    const html = await response.text();
    element.innerHTML = html;
    return element;
}

function start() {
    main.innerHTML = "";
}

router.start();


const deleteAccountElement = /** @type {HTMLAnchorElement} */
    (document.getElementById("delete-account"));
deleteAccountElement.addEventListener("click", async (event) => {
    event.preventDefault();
    if (!confirm("Confirm deletion of account?"))
        return;
    try {
        await register.deleteAccount();
        login.doLogout();
    } catch (/** @type {any} */ reason) {
        return alert(reason?.message ?? "Error.");
    }
    router.navigate("#/");
});

function refresh() {
    const elements = /** @type {NodeListOf<HTMLElement>} */
        (document.querySelectorAll(".logged-in, .guest"));
    
    Array.from(elements).forEach(element => element.hidden = 
        (element.classList.contains("logged-in") && ! login.userIsLoggedIn()) ||
        (element.classList.contains("guest") && login.userIsLoggedIn()))
}

const index = { main, dialog, setInnerHTML, start, refresh }
export { index };