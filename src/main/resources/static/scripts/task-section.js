import { Task } from "./Task.js";
import { tasks } from "./tasks.js";

/** @param {number} [id] */
async function start(id) {
    const method = id ? "PUT" : "POST";

    const dialog = /** @type {HTMLDialogElement} */
        (document.querySelector("#task dialog"));
    const form = /** @type {HTMLFormElement} */
        (dialog.querySelector("form"));
    const descriptionInput = /** @type {HTMLInputElement} */
        (form.elements.namedItem("description"));
    const dueDateInput = /** @type {HTMLInputElement} */
        (form.elements.namedItem("due-date"));

    if (method === "PUT") {
        const originalTask = await fetch(`/api/tasks/${id}`)
            .then(response => response.json())
            .then(t => new Task(t));

        descriptionInput.value = originalTask.description;
        
        const localDate = new Date(
            originalTask.dueDate.getTime() + originalTask.dueDate.getTimezoneOffset() * -60 * 1000);

        dueDateInput.value = localDate.toISOString().slice(0, 16);
    }

    form.addEventListener("submit", (_event) => fetch("/api/tasks", {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(/** @type {import("./Task").ITask} */
            ({ id, description: descriptionInput.value, dueDate: new Date(dueDateInput.value) }))
    }).then(tasks.start));

    dialog.showModal();
}

const taskSection = { start };
export { taskSection };