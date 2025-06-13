import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Categoria } from '../models/categoria.model';

@Injectable({
    providedIn: 'root'
})
export class CategoriaService {
    private readonly STORAGE_KEY = 'categorias';
    private categorias: Categoria[] = [];
    private categoriasSubject = new BehaviorSubject<Categoria[]>([]);

    constructor() {
        this.cargarCategorias();
    }

    private cargarCategorias(): void {
        const categoriasGuardadas = localStorage.getItem(this.STORAGE_KEY);
        if (categoriasGuardadas) {
            this.categorias = JSON.parse(categoriasGuardadas);
        } else {
            // Datos de prueba
            this.categorias = [
                {
                    id: 1,
                    nombre: 'Trabajo',
                    color: '#FF5733',
                    fechaCreacion: new Date(),
                    fechaActualizacion: new Date()
                },
                {
                    id: 2,
                    nombre: 'Personal',
                    color: '#33FF57',
                    fechaCreacion: new Date(),
                    fechaActualizacion: new Date()
                },
                {
                    id: 3,
                    nombre: 'Estudio',
                    color: '#3357FF',
                    fechaCreacion: new Date(),
                    fechaActualizacion: new Date()
                }
            ];
        }
        this.categoriasSubject.next(this.categorias);
    }

    private guardarCategorias(): void {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.categorias));
        this.categoriasSubject.next(this.categorias);
    }

    obtenerCategorias(): Observable<Categoria[]> {
        return this.categoriasSubject.asObservable();
    }

    agregarCategoria(categoria: Omit<Categoria, 'id' | 'fechaCreacion' | 'fechaActualizacion'>): void {
        const nuevaCategoria: Categoria = {
            ...categoria,
            id: Date.now(),
            fechaCreacion: new Date(),
            fechaActualizacion: new Date()
        };
        this.categorias.push(nuevaCategoria);
        this.guardarCategorias();
    }

    actualizarCategoria(id: number, cambios: Partial<Categoria>): void {
        const index = this.categorias.findIndex(c => c.id === id);
        if (index !== -1) {
            this.categorias[index] = {
                ...this.categorias[index],
                ...cambios,
                fechaActualizacion: new Date()
            };
            this.guardarCategorias();
        }
    }

    eliminarCategoria(id: number): void {
        this.categorias = this.categorias.filter(c => c.id !== id);
        this.guardarCategorias();
    }
} 