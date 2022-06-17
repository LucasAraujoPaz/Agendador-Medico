/** 
 * @typedef {{
 * id: number,
 * description: string,
 * dueDate: Date
 * }} ITask 
 * */

/** @implements {ITask} */
class Task {

    /** 
     * @param {{
     * id: number,
     * description: string,
     * dueDate: Date | string
     * }} _
     */
    constructor({ id, description, dueDate }) {
        this.id = id;
        this.description = description;
        this.dueDate = new Date(dueDate);
    }
}

export { Task };