import { login } from "./login.js";

const main = /** @type {HTMLElement} */
    (document.querySelector("main"));

const loginButton = /** @type {HTMLButtonElement} */
    (document.querySelector("nav [name='login']"));

loginButton.addEventListener(
    "click",
    () => fillInnerHtml({ url: "/login.html" })
        .then(login.start)
);

/** @param {{url: string, element?: HTMLElement}} _ */
const fillInnerHtml = async ({ url, element = main }) => {
    const response = await fetch(url, { cache: "no-cache" });
    const html = await response.text();
    element.innerHTML = html;
    return element;
}

const index = { main, fillInnerHtml }
export { index };