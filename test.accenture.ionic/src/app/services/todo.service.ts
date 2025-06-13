import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Tarea } from '../models/tarea.model';
import { Categoria } from '../models/categoria.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private categorias: Categoria[] = [
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
    },
    { 
      id: 4, 
      nombre: 'Hogar',
      color: '#F333FF',
      fechaCreacion: new Date(),
      fechaActualizacion: new Date()
    }
  ];

  private tareas: Tarea[] = [
    {
      id: 1,
      titulo: 'Completar informe',
      descripcion: 'Finalizar el informe mensual de ventas',
      categoriaId: 1,
      completada: false,
      fechaCreacion: new Date(),
      fechaActualizacion: new Date()
    },
    {
      id: 2,
      titulo: 'Ir al gimnasio',
      descripcion: 'Entrenamiento de fuerza',
      categoriaId: 2,
      completada: true,
      fechaCreacion: new Date(),
      fechaActualizacion: new Date()
    }
  ];

  constructor() { }

  getCategorias(): Observable<Categoria[]> {
    return of(this.categorias).pipe(delay(500));
  }

  agregarTarea(tarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'>): Observable<Tarea> {
    const nuevaTarea: Tarea = {
      ...tarea,
      id: this.tareas.length + 1,
      completada: false,
      fechaCreacion: new Date(),
      fechaActualizacion: new Date()
    };
    this.tareas.push(nuevaTarea);
    return of(nuevaTarea).pipe(delay(500));
  }

  getTareas(): Observable<Tarea[]> {
    return of(this.tareas).pipe(delay(500));
  }

  actualizarTarea(tarea: Tarea): Observable<Tarea> {
    const index = this.tareas.findIndex(t => t.id === tarea.id);
    if (index !== -1) {
      this.tareas[index] = {
        ...tarea,
        fechaActualizacion: new Date()
      };
      return of(this.tareas[index]).pipe(delay(500));
    }
    return of(tarea).pipe(delay(500));
  }

  eliminarTarea(id: number): Observable<void> {
    this.tareas = this.tareas.filter(t => t.id !== id);
    return of(void 0).pipe(delay(500));
  }
} 