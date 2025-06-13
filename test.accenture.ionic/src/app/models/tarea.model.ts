export interface Tarea {
    id: number;
    titulo: string;
    descripcion: string;
    completada: boolean;
    categoriaId: number;
    fechaCreacion: Date;
    fechaActualizacion: Date;
} 