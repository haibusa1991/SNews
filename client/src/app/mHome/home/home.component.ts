import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ArticleService} from "../../core/article-service/article.service";
import {ArticleOverviewData} from "../../utils/types";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  articleOverviews: ArticleOverviewData[] = [];

  constructor(private router: Router,
              private articleService: ArticleService) {
  }

  ngOnInit(): void {
    this.articleService.getHomeArticles$().subscribe(e => this.articleOverviews = e);
  }

  onNavigate(targetUrl:string){
    this.router.navigateByUrl(targetUrl);
  }

}
