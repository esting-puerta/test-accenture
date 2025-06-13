import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Tarea } from '../models/tarea.model';
import { Categoria } from '../models/categoria.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

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

  agregarTarea(tarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'>): Observable<Tarea> {
    const id = Math.floor(Math.random() * (1000000 - 100 + 1)) + 100;
    const nuevaTarea: Tarea = {
      ...tarea,
      id: id,
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