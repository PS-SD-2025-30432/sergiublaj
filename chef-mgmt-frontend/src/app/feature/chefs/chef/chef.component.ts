import { NgClass } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { ROUTES } from '../../../core/config/routes.enum';
import { AuthService } from '../../../core/services/auth/auth.service';
import { ChefService } from '../../../core/services/chef/chef.service';
import { ModalService } from '../../../core/services/modal/modal.service';
import { ModalType } from '../../../shared/models/modal-type.enum';
import { UserResponse } from '../../profile/models/user-response.model';
import { Role } from '../../profile/models/user-role.enum';


@Component({
  selector: 'app-chef',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './chef.component.html',
  styleUrl: './chef.component.scss'
})
export class ChefComponent implements OnInit, OnDestroy {
  chefId!: string;
  chefForm!: FormGroup;
  editMode = false;
  subject$ = new Subject<void>();
  userSubscription?: Subscription;
  loggedUser?: UserResponse;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private chefService: ChefService,
    private authService: AuthService,
    private modalService: ModalService
  ) { }

  ngOnInit(): void {
    this.fetchChefId();
    this.fetchChef();
    this.watchChefDeletion();
    this.watchUser();
  }

  ngOnDestroy(): void {
    this.subject$.next();
    this.subject$.complete();
    this.userSubscription?.unsubscribe();
  }

  private fetchChefId(): void {
    this.chefId = this.route.snapshot.paramMap.get('id')!;
  }

  private fetchChef(): void {
    this.chefService.getById(this.chefId).subscribe((chef) => {
      this.chefForm = this.fb.group({
        id: [
          { value: chef.id, disabled: true },
          [ Validators.required ]
        ],
        name: [
          { value: chef.name, disabled: true },
          [ Validators.required ]
        ],
        rating: [
          { value: chef.numberOfStars, disabled: true },
          [ Validators.required, Validators.min(0), Validators.max(5) ]
        ],
        cnp: [
          { value: chef.cnp, disabled: true },
          [ Validators.required, Validators.pattern(/^\d{13}$/) ]
        ],
        birthDate: [
          { value: new Date(chef.birthDate).toISOString().split('T')[0], disabled: true },
          [ Validators.required ]
        ]
      });
    });
  }

  private watchUser(): void {
    this.userSubscription = this.authService.user$
      .subscribe(response => this.loggedUser = response);
  }

  toggleEdit(): void {
    if (this.chefForm.invalid) {
      this.chefForm.markAllAsTouched();
      return;
    }

    this.editMode = !this.editMode;

    if (this.editMode) {
      this.chefForm.enable();
      this.chefForm.get('id')?.disable();
    } else {
      const updatedChef = {
        ...this.chefForm.getRawValue(),
        birthDate: new Date(this.chefForm.value.birthDate).toISOString(),
        numberOfStars: this.chefForm.value.rating
      };

      this.chefService.update(updatedChef.id, updatedChef).subscribe({
        next: () => {
          this.chefForm.disable();
          this.modalService.open('Success', 'Chef has been successfully updated!', ModalType.SUCCESS);
        },
        error: (error: HttpErrorResponse) => {
          this.modalService.open('Error', error.error.message, ModalType.ERROR);
        }
      });
    }
  }

  deleteChef(): void {
    this.modalService.open('Delete', 'Are you sure you want to delete this chef?', ModalType.CONFIRM);
  }

  private watchChefDeletion(): void {
    this.modalService.confirm$
      .pipe(takeUntil(this.subject$))
      .subscribe(() => {
        this.chefService.delete(this.chefId).subscribe({
          next: () => {
            this.modalService.open('Deleted', 'Chef has been deleted.', ModalType.SUCCESS);
            this.router.navigateByUrl(ROUTES.CHEFS).then();
          },
          error: (error: HttpErrorResponse) => {
            this.modalService.open('Error', error.error.message, ModalType.ERROR);
          }
        });
      });
  }

  protected readonly Role = Role;
}
