import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-todo-list',
    templateUrl: './todo-list.component.html',
    styleUrls: ['./todo-list.component.scss'],
    standalone: false,
})
export class TodoListComponent implements OnInit {
    
    categoriaSeleccionada: number = 0;
    showNewTaskForm: boolean = false;
    showFilterSection: boolean = false;
    showTaskList: boolean = false;

    constructor() {}

    ngOnInit(): void {}

    selectAction(action: string): void {
        if (action === 'create') {
            this.showNewTaskForm = !this.showNewTaskForm;
            this.showFilterSection = false;
            this.showTaskList = false;
        } else if (action === 'filter') {
            this.showFilterSection = !this.showFilterSection;
            this.showNewTaskForm = false;
            this.showTaskList = true;
        }
    }

    goBackToActions(): void {
        this.showNewTaskForm = false;
        this.showFilterSection = false;
        this.showTaskList = true;
    }

} 