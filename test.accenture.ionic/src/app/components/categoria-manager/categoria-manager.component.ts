import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { Categoria } from '../../models/categoria.model';
import { Observable } from 'rxjs';
import { IonicModule, ModalController } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
    selector: 'app-categoria-manager',
    templateUrl: './categoria-manager.component.html',
    styleUrls: ['./categoria-manager.component.scss'],
    standalone: true,
    imports: [IonicModule, FormsModule, CommonModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CategoriaManagerComponent implements OnInit {
    
    categorias$: Observable<Categoria[]>;
    nuevaCategoria: Partial<Categoria> = {
        nombre: '',
        color: '#000000'
    };
    categoriaEditando: Categoria | null = null;
    isModalOpen = false;

    predefinedColors = [
        '#FF5733', // Rojo
        '#33FF57', // Verde
        '#3357FF', // Azul
        '#FF33F6', // Rosa
        '#33FFF6', // Turquesa
        '#F6FF33', // Amarillo
        '#FF8C33', // Naranja
        '#8C33FF', // Púrpura
        '#33FF8C', // Verde claro
        '#FF3333', // Rojo claro
        '#3333FF', // Azul claro
        '#33FF33'  // Verde brillante
    ];

    constructor(private categoriaService: CategoriaService) {
        this.categorias$ = this.categoriaService.obtenerCategorias();
    }

    ngOnInit(): void {}

    agregarCategoria(): void {
        if (this.nuevaCategoria.nombre) {
            this.categoriaService.agregarCategoria(this.nuevaCategoria as Categoria);
            this.nuevaCategoria = {
                nombre: '',
                color: '#000000'
            };
        }
    }

    iniciarEdicion(categoria: Categoria): void {
        this.categoriaEditando = { ...categoria };
        this.isModalOpen = true;
    }

    guardarEdicion(): void {
        if (this.categoriaEditando) {
            this.categoriaService.actualizarCategoria(
                this.categoriaEditando.id,
                this.categoriaEditando
            );
            this.cancelarEdicion();
        }
    }

    cancelarEdicion(): void {
        this.categoriaEditando = null;
        this.isModalOpen = false;
    }

    eliminarCategoria(id: number): void {
        this.categoriaService.eliminarCategoria(id);
    }

    selectColor(color: string): void {
        if (this.categoriaEditando) {
            this.categoriaEditando.color = color;
        } else {
            this.nuevaCategoria.color = color;
        }
    }
} 