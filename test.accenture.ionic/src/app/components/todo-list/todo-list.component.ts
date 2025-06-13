import { Component, OnInit } from '@angular/core';
import { TareaService } from '../../services/tarea.service';
import { CategoriaService } from '../../services/categoria.service';
import { Tarea } from '../../models/tarea.model';
import { Categoria } from '../../models/categoria.model';
import { Observable } from 'rxjs';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
    selector: 'app-todo-list',
    templateUrl: './todo-list.component.html',
    styleUrls: ['./todo-list.component.scss'],
    standalone: false,
})
export class TodoListComponent implements OnInit {
    tareas$: Observable<Tarea[]>;
    categorias$: Observable<Categoria[]>;
    nuevaTarea: Partial<Tarea> = {
        titulo: '',
        descripcion: '',
        completada: false,
        categoriaId: 0
    };
    categoriaSeleccionada: number = 0;
    showNewTaskForm: boolean = false;
    showFilterSection: boolean = false;
    showTaskList: boolean = false;

    constructor(
        private tareaService: TareaService,
        private categoriaService: CategoriaService
    ) {
        this.tareas$ = this.tareaService.obtenerTareas();
        this.categorias$ = this.categoriaService.obtenerCategorias();
    }

    ngOnInit(): void {}

    selectAction(action: string): void {
        if (action === 'create') {
            this.showNewTaskForm = !this.showNewTaskForm;
            this.showFilterSection = false;
            this.showTaskList = false;
        } else if (action === 'filter') {
            this.showFilterSection = !this.showFilterSection;
            this.showNewTaskForm = false;
            this.showTaskList = false;
        }
    }

    goBackToActions(): void {
        this.showNewTaskForm = false;
        this.showFilterSection = false;
        this.showTaskList = true;
    }

    agregarTarea(): void {
        if (this.nuevaTarea.titulo && this.nuevaTarea.categoriaId) {
            this.tareaService.agregarTarea(this.nuevaTarea as Tarea);
            this.nuevaTarea = {
                titulo: '',
                descripcion: '',
                completada: false,
                categoriaId: 0
            };
            this.showNewTaskForm = false;
            this.showTaskList = true;
        }
    }

    toggleCompletada(tarea: Tarea): void {
        this.tareaService.actualizarTarea(tarea.id, {
            completada: !tarea.completada
        });
    }

    eliminarTarea(id: number): void {
        this.tareaService.eliminarTarea(id);
    }

    filtrarPorCategoria(categoriaId: number): void {
        this.categoriaSeleccionada = categoriaId;
        if (categoriaId === 0) {
            this.tareas$ = this.tareaService.obtenerTareas();
        } else {
            this.tareas$ = this.tareaService.obtenerTareasPorCategoria(categoriaId);
        }
    }

    onSegmentChange(event: any): void {
        const categoriaId = Number(event.detail.value) || 0;
        this.filtrarPorCategoria(categoriaId);
    }
} 