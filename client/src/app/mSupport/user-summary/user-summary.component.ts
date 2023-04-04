import {Component, Input} from '@angular/core';
import {User} from "../../utils/types";
import {userEndpoints} from "../../../environments/environment";
import {UserService} from "../../core/user-service/user.service";
import {Authority} from "../../utils/enums";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-summary',
  templateUrl: './user-summary.component.html',
  styleUrls: ['./user-summary.component.scss']
})
export class UserSummaryComponent {

  @Input()
  data!: User;
  protected readonly userEndpoints = userEndpoints;
  currentUsername!: string;
  isLoading: boolean = false;

  constructor(private userService: UserService, private confirmationSnackbar: MatSnackBar) {
    this.currentUsername = this.userService.getCurrentUsername();
  }

  addToAdministrators(username: string) {
    this.isLoading = true;
    this.userService.updateUserAuthorities$(username, Authority.ADD_ADMINISTRATOR).subscribe(() => {
      this.addRole('ADMINISTRATOR');
      this.isLoading = false;
    });
  }

  addToModerators(username: string) {
    this.isLoading = true;
    this.userService.updateUserAuthorities$(username, Authority.ADD_MODERATOR).subscribe(() => {
      this.addRole('MODERATOR');
      this.isLoading = false;
    });
  }

  removeFromAdministrators(username: string) {
    this.isLoading = true;
    this.userService.updateUserAuthorities$(username, Authority.REMOVE_ADMINISTRATOR).subscribe(() => {
      this.removeRole("ADMINISTRATOR");
      this.isLoading = false;
    });
  }

  removeFromModerators(username: string) {
    this.isLoading = true;
    this.userService.updateUserAuthorities$(username, Authority.REMOVE_MODERATOR).subscribe(() => {
      this.removeRole("MODERATOR");
      this.isLoading = false;
    });
  }

  private removeRole(role: string) {
    this.data.roles.forEach((val, index) => {
      if (val == role.toUpperCase()) {
        this.data.roles.splice(index, 1);
      }
    })
    this.confirmationSnackbar.open(`${this.data.username} бше успешно премахнат от ${role == "MODERATOR" ? 'модератори' : 'администратори'}.`, 'ОK', {duration: 3000});
  }

  private addRole(role: string) {
    if (role == 'MODERATOR' || role == 'ADMINISTRATOR') {
      this.data.roles.push(role);
      this.confirmationSnackbar.open(`${this.data.username} беше успешно добавен към ${role == "MODERATOR" ? 'модератори' : 'администратори'}.`, 'ОK', {duration: 3000});
    }
  }
}
