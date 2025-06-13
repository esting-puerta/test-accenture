import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { TareaService } from './tarea.service';
import { Tarea } from '../models/tarea.model';

describe('TareaService', () => {
  let service: TareaService;
  const mockLocalStorage = {
    getItem: jest.fn(),
    setItem: jest.fn(),
    clear: jest.fn()
  };

  beforeEach(() => {
    Object.defineProperty(window, 'localStorage', {
      value: mockLocalStorage,
      writable: true
    });
    mockLocalStorage.getItem.mockReturnValue(null);
    mockLocalStorage.setItem.mockClear();
    mockLocalStorage.clear.mockClear();

    TestBed.configureTestingModule({
      providers: [TareaService]
    });
    service = TestBed.inject(TareaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should add a new task', fakeAsync(() => {
    const nuevaTarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Nueva Tarea',
      descripcion: 'Descripción de la nueva tarea',
      categoriaId: 1,
      completada: false
    };

    let result: Tarea[] = [];
    service.obtenerTareas().subscribe(tareas => {
      result = tareas;
    });
    tick();

    service.agregarTarea(nuevaTarea);
    tick();

    expect(result.length).toBe(1);
    expect(result[0].titulo).toBe('Nueva Tarea');
    expect(result[0].descripcion).toBe('Descripción de la nueva tarea');
    expect(result[0].categoriaId).toBe(1);
    expect(result[0].completada).toBe(false);
    expect(result[0].id).toBeTruthy();
    expect(result[0].fechaCreacion).toBeTruthy();
    expect(result[0].fechaActualizacion).toBeTruthy();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));

  it('should update a task', fakeAsync(() => {
    // First add a task
    const tarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Tarea Original',
      descripcion: 'Descripción Original',
      categoriaId: 1,
      completada: false
    };
    service.agregarTarea(tarea);
    tick();

    const cambios: Partial<Tarea> = {
      titulo: 'Tarea Actualizada',
      completada: true
    };

    let result: Tarea[] = [];
    service.obtenerTareas().subscribe(tareas => {
      result = tareas;
    });
    tick();

    service.actualizarTarea(result[0].id, cambios);
    tick();

    expect(result[0].titulo).toBe('Tarea Actualizada');
    expect(result[0].completada).toBe(true);
    expect(result[0].fechaActualizacion).toBeTruthy();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));

  it('should delete a task', fakeAsync(() => {
    // First add a task
    const tarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Tarea a Eliminar',
      descripcion: 'Descripción',
      categoriaId: 1,
      completada: false
    };
    service.agregarTarea(tarea);
    tick();

    let result: Tarea[] = [];
    service.obtenerTareas().subscribe(tareas => {
      result = tareas;
    });
    tick();

    const taskId = result[0].id;
    service.eliminarTarea(taskId);
    tick();

    expect(result.length).toBe(0);
    expect(result.find(t => t.id === taskId)).toBeUndefined();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));

  it('should get tasks by category', fakeAsync(() => {
    // Add tasks for different categories
    const tarea1: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Tarea Categoría 1',
      descripcion: 'Descripción 1',
      categoriaId: 1,
      completada: false
    };
    const tarea2: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      titulo: 'Tarea Categoría 2',
      descripcion: 'Descripción 2',
      categoriaId: 2,
      completada: false
    };
    service.agregarTarea(tarea1);
    tick();
    service.agregarTarea(tarea2);
    tick();

    let result: Tarea[] = [];
    service.obtenerTareasPorCategoria(1).subscribe(tareas => {
      result = tareas;
    });
    tick();

    expect(result.length).toBe(1);
    expect(result[0].categoriaId).toBe(1);
    expect(result[0].titulo).toBe('Tarea Categoría 1');
  }));
}); 