import { TestBed } from '@angular/core/testing';
import { TodoService } from './todo.service';
import { Tarea } from '../models/tarea.model';
import { fakeAsync, tick } from '@angular/core/testing';

describe('TodoService', () => {
  let service: TodoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TodoService]
    });
    service = TestBed.inject(TodoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all tasks', fakeAsync(() => {
    let result: Tarea[] = [];
    service.getTareas().subscribe(tareas => {
      result = tareas;
    });
    tick(500);
    expect(result.length).toBe(2);
    expect(result[0].titulo).toBe('Completar informe');
    expect(result[1].titulo).toBe('Ir al gimnasio');
  }));

  it('should add a new task', fakeAsync(() => {
    const nuevaTarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Nueva tarea',
      descripcion: 'Descripción de la nueva tarea',
      categoriaId: 1,
      completada: false
    };

    let result: Tarea | undefined;
    service.agregarTarea(nuevaTarea).subscribe(tarea => {
      result = tarea;
    });
    tick(500);

    expect(result).toBeTruthy();
    expect(result?.titulo).toBe('Nueva tarea');
    expect(result?.descripcion).toBe('Descripción de la nueva tarea');
    expect(result?.categoriaId).toBe(1);
    expect(result?.completada).toBe(false);
    expect(result?.id).toBeTruthy();
    expect(result?.fechaCreacion).toBeTruthy();
    expect(result?.fechaActualizacion).toBeTruthy();
  }));

  it('should update a task', fakeAsync(() => {
    const tareaActualizada: Tarea = {
      id: 1,
      titulo: 'Informe actualizado',
      descripcion: 'Nueva descripción',
      categoriaId: 1,
      completada: true,
      fechaCreacion: new Date(),
      fechaActualizacion: new Date()
    };

    let result: Tarea | undefined;
    service.actualizarTarea(tareaActualizada).subscribe(tarea => {
      result = tarea;
    });
    tick(500);

    expect(result).toBeTruthy();
    expect(result?.titulo).toBe('Informe actualizado');
    expect(result?.descripcion).toBe('Nueva descripción');
    expect(result?.completada).toBe(true);
  }));

  it('should delete a task', fakeAsync(() => {
    const initialLength = 2;
    let result: void | undefined;

    service.eliminarTarea(1).subscribe(() => {
      result = undefined;
    });
    tick(500);

    expect(result).toBeUndefined();
    service.getTareas().subscribe(tareas => {
      expect(tareas.length).toBe(initialLength - 1);
      expect(tareas.find(t => t.id === 1)).toBeUndefined();
    });
    tick(500);
  }));
}); 