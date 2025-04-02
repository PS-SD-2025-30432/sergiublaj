import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PaginationComponent } from '../../../core/components/pagination/pagination.component';
import { SearchBarComponent } from '../../../core/components/search-bar/search-bar.component';
import { ChefService } from '../../../core/services/chef/chef.service';
import { buildChefFilterDTOFromSearchBy } from '../../../core/utils/rest-utils';
import { ChefResponseDTO } from '../models/chef-response-dto.model';


@Component({
  selector: 'app-chefs',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    CommonModule,
    SearchBarComponent,
    PaginationComponent
  ],
  templateUrl: './chefs.component.html',
  styleUrl: './chefs.component.scss'
})
export class ChefsComponent implements OnInit {

  chefs: ChefResponseDTO[] = [];
  loading = true;
  error: string | null = null;
  addChefForm!: FormGroup;
  showAddForm = false;
  currentPage = 0;
  totalPages = -1;

  constructor(
    private chefService: ChefService,
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const searchBy = params.get('searchBy') ?? '';
      this.currentPage = +(params.get('page') ?? '0');
      this.fetchChefs(searchBy, this.currentPage);
    });
    this.buildChefForm();
  }

  private fetchChefs(searchBy: string, page: number): void {
    this.loading = true;
    const filter = buildChefFilterDTOFromSearchBy(searchBy, page);

    this.chefService.getAll(filter).subscribe({
      next: (response) => {
        this.chefs = response.elements;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load chefs';
        this.loading = false;
      }
    });
  }


  private buildChefForm(): void {
    this.addChefForm = this.fb.group({
      name: [ '', [ Validators.required ] ],
      cnp: [ '', [ Validators.required, Validators.pattern(/^\d{13}$/) ] ],
      birthDate: [ '', [ Validators.required ] ],
      rating: [ 0, [ Validators.required, Validators.min(0), Validators.max(5) ] ]
    });
  }

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
  }

  saveChef(): void {
    if (this.addChefForm.valid) {
      const newChef = {
        ...this.addChefForm.value,
        numberOfStars: this.addChefForm.value.rating,
        birthDate: new Date(this.addChefForm.value.birthDate).toISOString()
      };
      this.chefService.save(newChef).subscribe({
        next: (savedChef) => {
          this.chefs.push(savedChef);
          this.addChefForm.reset();
          this.showAddForm = false;
        },
        error: (error: HttpErrorResponse) => {
          alert(error.error.message);
        }
      });
    }
  }

  onPageChange(page: number): void {
    console.log('Page changed to:', page);
  }
}
