import { index } from "./index.js";
import { Task } from "./Task.js";
import { taskSection } from "./task-section.js";

async function start() {

    /**@type {Task[]} */
    const tasks = await fetch("/api/tasks")
        .then(response => response.json())
        .then((/** @type {any[]} */ array) => array.map(item => new Task(item)));

    const tbody = /** @type {HTMLTableSectionElement} */
        (document.querySelector("#tasks tbody"));

    const rows = tasks.reduce((previous, current) =>
        `${previous}
        <tr>
            <td>${current.description}</td>
            <td>
                <time datetime="${current.dueDate}">
                ${current.dueDate}
                </time>
            </td>
            <td>
                <button class="edit" data-id="${current.id}">Edit</button>
                <button class="delete" data-id="${current.id}">Delete</button>
            </td>
        </tr>`,
        "");

    tbody.innerHTML = rows;

    const buttons = Array.from(tbody.querySelectorAll("button"));

    buttons.filter(button => button.classList.contains("edit"))
        .forEach(editButton => editButton.onclick = () =>
            index.fillInnerHtml({ url: "/templates/task.html", element: document.getElementById("dialog") })
                .then(_ => taskSection.start(Number(editButton.dataset["id"]))));

    buttons.filter(button => button.classList.contains("delete"))
        .forEach(deleteButton => deleteButton.onclick = () =>
            deleteTask(Number(deleteButton.dataset["id"]))
                .then(start));

    const addTaskButton = /** @type {HTMLButtonElement} */
        (document.querySelector("#tasks button[name='add-task']"));

    addTaskButton.onclick = () =>
        index.fillInnerHtml({ url: "/templates/task.html", element: document.getElementById("dialog") })
            .then(_ => taskSection.start());
};

/** @param {number} id */
async function deleteTask(id) {
    await fetch(`/api/tasks/${id}`, {
        method: "DELETE"
    });
}

const tasks = { start };
export { tasks };