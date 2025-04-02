import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ROUTES } from '../../../core/config/routes.enum';
import { ChefService } from '../../../core/services/chef/chef.service';


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
export class ChefComponent implements OnInit {
  chefId!: string;
  chefForm!: FormGroup;
  editMode = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private chefService: ChefService
  ) { }

  ngOnInit(): void {
    this.fetchChefId();
    this.fetchChef();
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

  toggleEdit(): void {
    if (this.chefForm.invalid) {
      this.chefForm.markAllAsTouched();
      return;
    }

    this.editMode = !this.editMode;

    if (!this.editMode) {
      const updatedChef = {
        ...this.chefForm.getRawValue(),
        birthDate: new Date(this.chefForm.value.birthDate).toISOString(),
        numberOfStars: this.chefForm.value.rating
      };

      this.chefService.update(updatedChef.id, updatedChef).subscribe(() => {
        this.chefForm.disable();
      });
    } else {
      this.chefForm.enable();
      this.chefForm.get('id')?.disable();
    }
  }

  deleteChef(): void {
    if (confirm('Are you sure you want to delete this chef?')) {
      this.chefService.delete(this.chefId).subscribe(() => {
        alert('Chef deleted!');
        this.router.navigateByUrl(ROUTES.CHEFS).then();
      });
    }
  }
}
