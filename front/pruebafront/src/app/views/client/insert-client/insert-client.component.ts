import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Cliente } from '../../../model/Cliente';
import { ClientService } from '../../../_service/client.service';
import { map, Observable, startWith } from 'rxjs';

@Component({
  selector: 'app-insert-client',
  templateUrl: './insert-client.component.html',
  styleUrl: './insert-client.component.css'
})
export class InsertClientComponent implements OnInit {
  options: string[] = [
    'Bogotá',
    'Medellín',
    'Cali',
    'Barranquilla',
    'Cartagena',
    'Cúcuta',
    'Bucaramanga',
    'Pereira',
    'Santa Marta',
    'Manizales',
    'Ibagué',
    'Villavicencio',
    'Neiva',
    'Armenia',
    'Montería',
    'Popayán',
    'Sincelejo',
    'Valledupar',
    'Pasto',
    'Tunja'
  ];
  filteredOptions!: Observable<string[]>;
  form!: FormGroup;
  document!: string;
  edicion!: boolean;
  hide = true;
  genero = "Seleccione el genero";

  constructor(private clienteService: ClientService, public dialogRef: MatDialogRef<InsertClientComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private infoSnackBar: MatSnackBarModule) { }

  ngOnInit() {
    this.edicion = this.data.edit;
    this.inicializarFormularioVacio();
    if (this.edicion == true) {
      this.cargarDatos();
    }
    this.filteredOptions = this.form.get('ciudad')!.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || ''))
    );

  }

  inicializarFormularioVacio() {
    if (this.edicion == true) {
      this.form = new FormGroup({
        'documento': new FormControl('', [Validators.required]),
        'nombre': new FormControl('', [Validators.required]),
        'apellidos': new FormControl('', [Validators.required]),
        'fechaNacimiento': new FormControl('', [Validators.required]),
        'ciudad': new FormControl('', [Validators.required]),
        'correo': new FormControl('', [Validators.required]),
        'telefono': new FormControl('', [Validators.required]),
        'ocupacion': new FormControl('', [Validators.required]),

      });
    } else {
      this.form = new FormGroup({
        'documento': new FormControl('', [Validators.required]),
        'nombre': new FormControl('', [Validators.required]),
        'apellidos': new FormControl('', [Validators.required]),
        'fechaNacimiento': new FormControl('', [Validators.required]),
        'ciudad': new FormControl('', [Validators.required]),
        'correo': new FormControl('', [Validators.required]),
        'telefono': new FormControl('', [Validators.required]),
        'ocupacion': new FormControl('', [Validators.required]),
      });
    }
  }

  cargarDatos() {
    this.clienteService.listarPorDocumento(this.data.document).subscribe(data => {
      this.document = data.document;
      this.form.get("documento")!.setValue(data.document);
      this.form.get("nombre")!.setValue(data.names);
      this.form.get("apellidos")!.setValue(data.lastNames);
      const parts = data.bornDate.split('-');
      const date = new Date(+parts[0], +parts[1] - 1, +parts[2]);
      this.form.get("fechaNacimiento")!.setValue(date);
      this.form.get("ciudad")!.setValue(data.city);
      this.form.get("correo")!.setValue(data.email);
      this.form.get("telefono")!.setValue(data.phone);
      this.form.get("ocupacion")!.setValue(data.occupation);
    });

  }

  guardar() {
    let cliente = new Cliente();
    cliente.document = this.form.value['documento'];
    cliente.names = this.form.value['nombre'];
    cliente.lastNames = this.form.value['apellidos'];
    const rawDate: Date = this.form.value['fechaNacimiento'];
    cliente.bornDate = rawDate.toISOString().split('T')[0]; 
    cliente.city = this.form.value['ciudad'];
    cliente.email = this.form.value['correo'];
    cliente.phone = this.form.value['telefono'];
    cliente.occupation = this.form.value['ocupacion'];

    if (this.edicion === true) {
      cliente.document = this.document;
      console.log(cliente)
      this.clienteService.editar(cliente).subscribe(() => {
        this.form.reset();
        this.dialogRef.close();
        this.clienteService.mensajeCambio.next('Cliente editado satisfactoreamente');
      });
    } else {
      this.clienteService.guardar(cliente).subscribe(() => {
        this.form.reset();
        this.dialogRef.close();
        this.clienteService.mensajeCambio.next('Cliente agregado satisfactoreamente');
      });
    }
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }
}
