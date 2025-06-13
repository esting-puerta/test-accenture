import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { Categoria } from '../../models/categoria.model';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-categoria-manager',
    templateUrl: './categoria-manager.component.html',
    styleUrls: ['./categoria-manager.component.scss'],
    standalone: false,
})
export class CategoriaManagerComponent implements OnInit {


    categoria: Categoria = {
        id: 0,
        nombre: '',
        color: '#3498db',
        fechaCreacion: new Date(),
        fechaActualizacion: new Date(),
    };
    categoriaEditando: Categoria | null = null;
    isModalOpen = false;
    showNewTaskForm = false;
    showTaskList = false;


    constructor() {}

    ngOnInit(): void {}

    selectAction(action: 'create' | 'filter'): void {
        if (action === 'create') {
            this.showNewTaskForm = true;
            this.showTaskList = false;
        } else if (action === 'filter') {
            this.showTaskList = true;
            this.showNewTaskForm = false;
        }
    }

    agregarCategoria(): void {
        if (this.categoria.nombre) {
            this.categoria = {
                id: 0,
                nombre: '',
                color: '#3498db',
                fechaCreacion: new Date(),
                fechaActualizacion: new Date(),
            };
        }
    }

    actualizar(categoria: Categoria): void {
        this.categoria = { ...categoria };
    }


} 