import {Component, OnInit} from '@angular/core';
import {UserService} from "../../core/user-service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit{


  constructor(private userService:UserService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.userService.logout$().subscribe( );
    this.router.navigateByUrl('/');
  }
}
