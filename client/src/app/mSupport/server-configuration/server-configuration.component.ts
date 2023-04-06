import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ConfigurationService} from "../../core/configuration/configuration.service";
import {ServerConfiguration} from "../../utils/types";
import {switchMap} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-server-configuration',
  templateUrl: './server-configuration.component.html',
  styleUrls: ['./server-configuration.component.scss']
})
export class ServerConfigurationComponent implements OnInit {
  serverState: ServerConfiguration | null = null;

  @Output()
  onCancelEvent = new EventEmitter<void>

  constructor(private configurationService: ConfigurationService, private confirmationSnackbar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.configurationService.getState$().subscribe(s => {
      this.serverState = s;
    })
  }

  onSettingsSave() {
    let currentState = this.serverState;
    this.serverState = null;
    this.configurationService.setState$(currentState!).pipe(
      switchMap(() => this.configurationService.getState$())
    ).subscribe(s => {
      this.serverState = s;
      this.confirmationSnackbar.open(`Настройките бяха запаметени успешно.`, 'ОK', {duration: 3000});
    })
  }

  onCancel() {
    this.onCancelEvent.emit();
  }
}
