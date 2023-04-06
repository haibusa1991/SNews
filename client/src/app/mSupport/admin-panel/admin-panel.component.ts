import {Component} from '@angular/core';
import {ConfigurationService} from "../../core/configuration/configuration.service";
import {ServerConfiguration} from "../../utils/types";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent {

  isSearchVisible: boolean = false;
  isServerConfigVisible: boolean = false;


  toggleSearch() {
    this.isSearchVisible = !this.isSearchVisible;
    this.isServerConfigVisible = false;
  }

  toggleServerConfig() {
    this.isServerConfigVisible = !this.isServerConfigVisible;
    this.isSearchVisible = false;
  }

}
