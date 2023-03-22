import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EventProviderService} from "../../core/event-provider/event-provider.service";
import {SearchService} from "../../core/search/search.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Router} from "@angular/router";

const ANIMATION_DURATION = '50ms'
const errorAnimation =  trigger('errorAnimation', [
    state('open', style({
      'height': '100%',
      'line-height': '*',
      'font-size': '*'
    })),
    transition(':enter', [
      style({
        'height': '0',
        'line-height': '0',
        'font-size': '0'
      }),
      animate(ANIMATION_DURATION)
    ]),
    transition(':leave', [
      animate(ANIMATION_DURATION, style({
        'height': '0',
        'line-height': '0',
        'font-size': '0'
      }))
    ])
  ]);


@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
  animations: [errorAnimation]
})

export class SearchBarComponent implements OnInit {
  searchForm = new FormGroup({
    searchField: new FormControl('')
  })

  isQueryTooShort = false;

  @ViewChild('searchField') searchField!: ElementRef;

  constructor(private eventProvider: EventProviderService,
              private router:Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    let query = this.searchForm.controls.searchField.value!;
    if (!query || query.length < 3) {
      this.isQueryTooShort = true;
      return;
    }

    this.isQueryTooShort = false;
    this.router.navigateByUrl('/news/search-results/'+query)
    // console.log(result); //todo remove this
    this.searchField.nativeElement.blur();
    this.eventProvider.emitBackgroundClick();
    this.searchForm.reset();
  }
}
