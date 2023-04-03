import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: [
    './user-panel.component.scss',
    './user-panel-shared.component.scss',
    './user-panel-avatar.component.scss',
    './user-panel-moderation.component.scss',
    './user-panel-credentials.component.scss']
})
export class UserPanelComponent implements OnInit {
  isEditEmailVisible:boolean=true;
  isEditPasswordVisible:boolean=true;

  ngOnInit(): void {
  }

  onEmailEdit(isHidden:boolean) {
    this.isEditPasswordVisible = !isHidden;
  }

  onPasswordEdit(isHidden:boolean) {
    this.isEditEmailVisible = !isHidden;
  }
}
