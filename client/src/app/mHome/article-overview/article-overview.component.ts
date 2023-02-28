import {Component, Input, OnInit} from '@angular/core';
import {ArticleOverviewData} from "../../types/types";

@Component({
  selector: 'app-article-overview',
  templateUrl: './article-overview.component.html',
  styleUrls: ['./article-overview.component.scss']
})
export class ArticleOverviewComponent implements OnInit {

  @Input()
  isSmallHeading: boolean = false


//todo remove defaults
  @Input()
  articleOverviewData: ArticleOverviewData = {
    heading: '"Вектор за атака". Как София даде милиони за китайски камери в градския транспорт',
    href: '/articles/article-url-goes-here',
    publishDate: '02 Февруари 2023 17:33',
    thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'

  };

  constructor() {
  }

  ngOnInit(): void {
  }

}
