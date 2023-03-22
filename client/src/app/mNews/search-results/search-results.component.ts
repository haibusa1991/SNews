import {Component, Input, OnInit} from '@angular/core';
import {Article, ArticleOverviewData} from "../../utils/types";
import {SearchService} from "../../core/search/search.service";
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {query} from "@angular/animations";

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit {

  articles: ArticleOverviewData[] = [];
  isLoading: boolean = true;
  hasNoResults: boolean = false;


  constructor(private searchService: SearchService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.pipe(
      switchMap(query => this.searchService.doSearch(query.get('keywords')!))
    ).subscribe({
      next: n => {
        this.articles = n;
        this.isLoading = false;
        this.hasNoResults = !(this.articles.length > 0);
      }
    });
  }

  onNavigate(url: string) {
    this.router.navigateByUrl(url);
  }
}
