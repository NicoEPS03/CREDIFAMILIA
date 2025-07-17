import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../../../_service/client.service';

@Component({
  selector: 'app-delete-client',
  templateUrl: './delete-client.component.html',
  styleUrl: './delete-client.component.css'
})
export class DeleteClientComponent {
  constructor(public dialogRef: MatDialogRef<DeleteClientComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private clientService: ClientService,) { }

  ngOnInit(): void {
  }

  eliminar() {
    console.log(this.data)
    this.clientService.eliminar(this.data.document).subscribe(data => {
      this.dialogRef.close();
      this.clientService.mensajeCambio.next('Cliente eliminado satisfactoreamente');
    });
  }
}
