import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoriaService } from '../../../services/categoria.service';
import { Categoria } from '../../../models/categoria.model';

@Component({
  selector: 'app-categoria-list',
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.scss'],
  standalone: false,
})
export class CategoriaListComponent implements OnInit {

  categorias$: Observable<Categoria[]>;
  @Output() return = new EventEmitter();
  @Output() editar = new EventEmitter<Categoria>();

  constructor(private categoriaService: CategoriaService) {
    this.categorias$ = this.categoriaService.obtenerCategorias();
  }

  ngOnInit() {}

  goBack() {
    this.return.emit();
  }

  iniciarEdicion(categoria: Categoria): void {
    this.editar.emit(categoria);
  }

  eliminarCategoria(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta categoría?')) {
      this.categoriaService.eliminarCategoria(id);
    }
  }
}
