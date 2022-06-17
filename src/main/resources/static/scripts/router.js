import { index } from "./index.js";
import { tasks } from "./tasks.js";
import { register } from "./register.js";
import { login } from "./login.js";

const setInnerHTMLAndStart = /** @param {{url: string, start: Function}} _ */
    ({ url, start }) => index.setInnerHTML({ url }).then(_ => start());

/** @type {{[route: string]: Function}} */
const routes = {
    "#/": () => index.start(),
    "#/tasks": () => setInnerHTMLAndStart({ url: "/templates/tasks.html", start: tasks.start }),
    "#/register": () => setInnerHTMLAndStart({ url: "/templates/register.html", start: register.start }),
    "#/login": () => setInnerHTMLAndStart({ url: "/templates/login.html", start: login.start }),
    "#/logout": () => { login.doLogout(); navigate("#/"); }
};

async function navigate(hash = "#") {
    if (location.hash !== hash)
        return location.hash = hash;

    if (hash in ["", "#", "#/"])
        return routes["#/"]?.();

    await routes[hash]?.();
    index.refresh();
}

const start = () => window.onload = window.onhashchange = () => router.navigate(location.hash);

const router = { start, navigate };
export { router };