import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Categoria } from 'src/app/models/categoria.model';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-categoria-form',
  templateUrl: './categoria-form.component.html',
  styleUrls: ['./categoria-form.component.scss'],
  standalone: false,
})
export class CategoriaFormComponent implements OnInit {

  predefinedColors: string[] = [
    '#3498db', // Azul
    '#2ecc71', // Verde
    '#e74c3c', // Rojo
    '#f1c40f', // Amarillo
    '#9b59b6', // Morado
    '#1abc9c', // Turquesa
    '#e67e22', // Naranja
    '#34495e'  // Azul oscuro
  ];

  @Output() return = new EventEmitter();
  @Input() type: number = 0;
  categoriaBase: Categoria = {
    id: 0,
    nombre: '',
    color: '#3498db',
    fechaCreacion: new Date(),
    fechaActualizacion: new Date(),
  };
  @Input() categoria: Categoria = this.categoriaBase;

  constructor(private categoriaService: CategoriaService, private toastService: ToastService) { }

  ngOnInit() { }

  goBack() {
    this.return.emit();
  }

  selectColor(color: string): void {
    this.categoria.color = color;
  }

  agregarCategoria(): void {
    const id = Math.floor(Math.random() * (1000000 - 100 + 1)) + 100;
    if (this.categoria.nombre.trim().length > 0) {
      this.categoria.id =  id;
      this.categoriaService.agregarCategoria(this.categoria as Categoria);
      this.toastService.showSuccess(' Categoria agregada!');
      this.categoria = this.categoriaBase;
    }
  }

  guardarEdicion(): void {
    if (this.categoria.nombre.trim().length > 0) {
      this.categoriaService.actualizarCategoria(
        this.categoria.id,
        this.categoria
      );

      this.toastService.showSuccess(' Categoria actualizada!');
      this.categoria = this.categoriaBase;
    }
  }
}
