import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Tarea } from '../models/tarea.model';

@Injectable({
    providedIn: 'root'
})
export class TareaService {
    private readonly STORAGE_KEY = 'tareas';
    private tareas: Tarea[] = [];
    private tareasSubject = new BehaviorSubject<Tarea[]>([]);

    constructor() {
        this.cargarTareas();
    }

    private cargarTareas(): void {
        const tareasGuardadas = localStorage.getItem(this.STORAGE_KEY);
        if (tareasGuardadas) {
            this.tareas = JSON.parse(tareasGuardadas);
            this.tareasSubject.next(this.tareas);
        }
    }

    private guardarTareas(): void {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.tareas));
        this.tareasSubject.next(this.tareas);
    }

    obtenerTareas(): Observable<Tarea[]> {
        return this.tareasSubject.asObservable();
    }

    agregarTarea(tarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'>): void {
        const nuevaTarea: Tarea = {
            ...tarea,
            id: Date.now(),
            fechaCreacion: new Date(),
            fechaActualizacion: new Date()
        };
        this.tareas.push(nuevaTarea);
        this.guardarTareas();
    }

    actualizarTarea(id: number, cambios: Partial<Tarea>): void {
        const index = this.tareas.findIndex(t => t.id === id);
        if (index !== -1) {
            this.tareas[index] = {
                ...this.tareas[index],
                ...cambios,
                fechaActualizacion: new Date()
            };
            this.guardarTareas();
        }
    }

    eliminarTarea(id: number): void {
        this.tareas = this.tareas.filter(t => t.id !== id);
        this.guardarTareas();
    }

    obtenerTareasPorCategoria(categoriaId: number): Observable<Tarea[]> {
        return new Observable(subscriber => {
            const tareasFiltradas = this.tareas.filter(t => t.categoriaId === categoriaId);
            subscriber.next(tareasFiltradas);
        });
    }
} 