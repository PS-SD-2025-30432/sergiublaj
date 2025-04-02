import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-pagination',
  imports: [],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.scss'
})
export class PaginationComponent implements OnInit {
  @Input() totalPages: number = 1;
  @Input() currentPage: number = 0;
  @Output() pageChange: EventEmitter<number> = new EventEmitter<number>();

  pages: number[] = [];

  constructor(private router: Router) {
    this.router = router;
  }

  ngOnInit(): void {
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i);
  }

  changePage(page: number): void {
    if (page < 0 || page > this.totalPages - 1|| page === this.currentPage) {
      return;
    }

    this.pageChange.emit(page);

    const currentUrl = this.router.url.split('?')[0];
    this.router.navigate([ currentUrl ], {
      queryParams: { page },
      queryParamsHandling: 'merge'
    }).then();
  }
}
