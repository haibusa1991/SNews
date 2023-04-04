import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../utils/types";
import {QueryService} from "../../core/query/query.service";

@Component({
  selector: 'app-users-search',
  templateUrl: './users-search.component.html',
  styleUrls: ['./users-search.component.scss']
})

export class UsersSearchComponent implements OnInit {
  searchForm = new FormGroup({
    searchField: new FormControl('')
  })

  isQueryTooShort: boolean = false;
  isLoading: boolean = false;
  lastQuery!: string;
  users: User[] = [];


  @ViewChild('searchField') searchField!: ElementRef;

  constructor(private queryService: QueryService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.users = [];
    this.lastQuery = '';
    let query = this.searchForm.controls.searchField.value!;
    this.lastQuery = query;
    if (!query || query.length < 3) {
      this.isQueryTooShort = true;
      return;
    }

    this.isQueryTooShort = false;
    this.searchField.nativeElement.blur();

    this.isLoading = true;
    this.queryService.findUsersByUsername$(query).subscribe(results => {
      this.users = results;
      this.isLoading = false;
    });
  }

}
