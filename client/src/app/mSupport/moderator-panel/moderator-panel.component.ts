import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-moderator-panel',
  templateUrl: './moderator-panel.component.html',
  styleUrls: ['./moderator-panel.component.scss']
})
export class ModeratorPanelComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    // this.router.navigateByUrl('/under-construction')
  }

}
