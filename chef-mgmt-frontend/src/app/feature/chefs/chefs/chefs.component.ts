import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ChefService } from '../../../core/services/chef/chef.service';
import { ModalService } from '../../../core/services/modal/modal.service';
import { buildChefFilterDTOFromSearchBy } from '../../../core/utils/rest-utils';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';
import { SearchBarComponent } from '../../../shared/components/search-bar/search-bar.component';
import { ModalType } from '../../../shared/models/modal-type.enum';
import { ChefResponse } from '../models/chef-response.model';


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

  chefs: ChefResponse[] = [];
  loading = true;
  error: string | null = null;
  addChefForm!: FormGroup;
  showAddForm = false;
  currentPage = 0;
  totalPages = -1;

  constructor(
    private chefService: ChefService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private modalService: ModalService
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
          this.modalService.open('Success', 'Chef has been successfully added!', ModalType.SUCCESS);
        },
        error: (error: HttpErrorResponse) => {
          this.modalService.open('Error', error.error.message, ModalType.ERROR);
        }
      });
    }
  }

  onPageChange(page: number): void {
    console.log('Page changed to:', page);
  }
}
