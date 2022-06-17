import { router } from "./router.js";
import { register } from "./register.js";
import { login } from "./login.js";

const main = /** @type {HTMLElement} */
    (document.querySelector("main"));
const dialog = /** @type {HTMLElement} */
    (document.querySelector("#dialog"));

function start() {
    main.innerHTML = "";
}

const deleteAccountElement = /** @type {HTMLAnchorElement} */
    (document.getElementById("delete-account"));
deleteAccountElement.addEventListener("click", async (event) => {
    event.preventDefault();
    register.onDeleteAccount();
});

function refresh() {
    const elements = /** @type {NodeListOf<HTMLElement>} */
        (document.querySelectorAll(".logged-in, .guest"));

    Array.from(elements).forEach(element => element.hidden =
        (element.classList.contains("logged-in") && !login.userIsLoggedIn()) ||
        (element.classList.contains("guest") && login.userIsLoggedIn()))
}

/** @param {{url: string, element?: HTMLElement }} _ */
const setInnerHTML = async ({ url, element = main }) => {
    const response = await fetch(url, { cache: "no-cache" });
    const html = await response.text();
    element.innerHTML = html;
    return element;
}

router.start();

const index = { main, dialog, setInnerHTML, start, refresh }
export { index };