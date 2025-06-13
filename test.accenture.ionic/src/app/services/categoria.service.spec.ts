import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { CategoriaService } from './categoria.service';
import { Categoria } from '../models/categoria.model';

describe('CategoriaService', () => {
  let service: CategoriaService;
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
      providers: [CategoriaService]
    });
    service = TestBed.inject(CategoriaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should load default categories when localStorage is empty', fakeAsync(() => {
    let result: Categoria[] = [];
    service.obtenerCategorias().subscribe(categorias => {
      result = categorias;
    });
    tick();

    expect(result.length).toBe(3);
    expect(result[0].nombre).toBe('Trabajo');
    expect(result[1].nombre).toBe('Personal');
    expect(result[2].nombre).toBe('Estudio');
  }));


  it('should add a new category', fakeAsync(() => {
    const nuevaCategoria: Omit<Categoria, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
      nombre: 'Nueva Categoría',
      color: '#FF0000'
    };

    let result: Categoria[] = [];
    service.obtenerCategorias().subscribe(categorias => {
      result = categorias;
    });
    tick();

    service.agregarCategoria(nuevaCategoria);
    tick();

    expect(result.length).toBe(4);
    expect(result[3].nombre).toBe('Nueva Categoría');
    expect(result[3].color).toBe('#FF0000');
    expect(result[3].id).toBeTruthy();
    expect(result[3].fechaCreacion).toBeTruthy();
    expect(result[3].fechaActualizacion).toBeTruthy();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));

  it('should update a category', fakeAsync(() => {
    const cambios: Partial<Categoria> = {
      nombre: 'Trabajo Actualizado',
      color: '#000000'
    };

    let result: Categoria[] = [];
    service.obtenerCategorias().subscribe(categorias => {
      result = categorias;
    });
    tick();

    service.actualizarCategoria(1, cambios);
    tick();

    expect(result[0].nombre).toBe('Trabajo Actualizado');
    expect(result[0].color).toBe('#000000');
    expect(result[0].fechaActualizacion).toBeTruthy();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));

  it('should delete a category', fakeAsync(() => {
    let result: Categoria[] = [];
    service.obtenerCategorias().subscribe(categorias => {
      result = categorias;
    });
    tick();

    const initialLength = result.length;
    service.eliminarCategoria(1);
    tick();

    expect(result.length).toBe(initialLength - 1);
    expect(result.find(c => c.id === 1)).toBeUndefined();
    expect(mockLocalStorage.setItem).toHaveBeenCalled();
  }));
}); 