import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InsertClientComponent } from './insert-client/insert-client.component';
import { DeleteClientComponent } from './delete-client/delete-client.component';
import { ClientService } from '../../_service/client.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrl: './client.component.css'
})
export class ClientComponent implements OnInit {
  form!: FormGroup;
  displayedColumns: string[] = ['document', 'names', 'lastNames', 'bornDate', 'city', 'email', 'phone', 'occupation', 'viable', 'active', 'action'];
  dataSource = new MatTableDataSource<any>();
  clientList: any[] = [];

  constructor(private clientService: ClientService, private dialog: MatDialog, private snackBar: MatSnackBar) { };

  ngOnInit(): void {
    this.form = new FormGroup({ 'document': new FormControl(null) });
    this.listar();
  }

  listar() {
    this.clientService.listar().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
    });
  }

  buscar() {
    if (!this.form.value['document']) {
      this.clientService.listar().subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
      });
    } else {
      this.clientService.listarPorDocumento(this.form.value['document']).subscribe(data => {
        this.clientList = [data];
        this.dataSource = new MatTableDataSource(this.clientList);
      });
    }
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Información', {
      duration: 2000,
    });
  }

  insertModal() {
    const dialogRef = this.dialog.open(InsertClientComponent, { data: { edit: false } });
    this.clientService.mensajeCambio.subscribe(data => {
      dialogRef.afterClosed().subscribe(result => {
        this.listar();
        this.openSnackBar(data);
      });
    });
  }

  editModal(document: any) {
    const dialogRef = this.dialog.open(InsertClientComponent, { data: { document: document, edit: true } });
    this.clientService.mensajeCambio.subscribe(data => {
      dialogRef.afterClosed().subscribe(result => {
        this.listar();
        this.openSnackBar(data);
      });
    });
  }

  deleteModal(document: any) {
    const dialogRef = this.dialog.open(DeleteClientComponent, { data: { document: document } });
    this.clientService.mensajeCambio.subscribe(data => {
      dialogRef.afterClosed().subscribe(result => {
        this.listar();
        this.openSnackBar(data);
      });
    });
  }
}
