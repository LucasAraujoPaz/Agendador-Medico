import { tasks } from "./tasks.js";
import { register } from "./register.js";
import { login } from "./login.js";

const main = /** @type {HTMLElement} */
    (document.querySelector("main"));

const tasksButton = /** @type {HTMLButtonElement} */
    (document.querySelector("nav [name='tasks']"));
const registerButton = /** @type {HTMLButtonElement} */
    (document.querySelector("nav [name='register']"));
const loginButton = /** @type {HTMLButtonElement} */
    (document.querySelector("nav [name='login']"));
const logoutButton = /** @type {HTMLButtonElement} */
    (document.querySelector("nav [name='logout']"));

tasksButton.addEventListener(
    "click",
    () => fillInnerHtml({ url: "/tasks.html" })
        .then(tasks.start)
);
registerButton.addEventListener(
    "click",
    () => fillInnerHtml({ url: "/register.html" })
        .then(register.start)
);
loginButton.addEventListener(
    "click",
    () => fillInnerHtml({ url: "/login.html" })
        .then(login.start)
);
logoutButton.addEventListener(
    "click",
    () => {
        login.doLogout();
        index.main.innerHTML = "";
    }
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