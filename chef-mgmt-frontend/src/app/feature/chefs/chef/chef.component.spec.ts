import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

import { ChefComponent } from './chef.component';
import { ChefService } from '../../../core/services/chef/chef.service';
import { AuthService } from '../../../core/services/auth/auth.service';
import { ModalService } from '../../../core/services/modal/modal.service';
import { mockChef, mockAdminUser, mockModeratorUser } from './chef.component.spec.data';

describe('ChefComponent', () => {
  let component: ChefComponent;
  let fixture: ComponentFixture<ChefComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChefComponent, HttpClientTestingModule, ReactiveFormsModule],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({ id: '1' })
            }
          }
        },
        {
          provide: ChefService,
          useValue: {
            getById: () => of(mockChef),
            update: () => of({}),
            delete: () => of({})
          }
        },
        {
          provide: AuthService,
          useValue: {
            user$: of(mockAdminUser)
          }
        },
        {
          provide: ModalService,
          useValue: {
            open: jasmine.createSpy(),
            confirm$: of(true)
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ChefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function hasTextDangerContaining(text: string): boolean {
    const elements = fixture.nativeElement.querySelectorAll('.text-danger');
    return Array.from(elements).some(el => (el as any).textContent.includes(text));
  }

  it('should create the component', () => {
    // then
    expect(component).toBeTruthy();
  });

  it('should show "No chef selected." if chefForm is undefined', () => {
    // given
    component.chefForm = undefined!;

    // when
    fixture.detectChanges();

    // then
    const msg = fixture.nativeElement.querySelector('.alert-info');
    expect(msg?.textContent).toContain('No chef selected.');
  });

  it('should show "Name is required." when name is touched and empty', () => {
    // given
    const control = component.chefForm.get('name');
    control?.enable();
    control?.setValue('');
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('Name is required.')).toBeTrue();
  });

  it('should show "Rating is required." when rating is empty', () => {
    // given
    const control = component.chefForm.get('rating');
    control?.enable();
    control?.setValue(null);
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('Rating is required.')).toBeTrue();
  });

  it('should show "Rating must be at least 0." when rating is negative', () => {
    // given
    const control = component.chefForm.get('rating');
    control?.enable();
    control?.setValue(-1);
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('Rating must be at least 0.')).toBeTrue();
  });

  it('should show "Rating cannot be more than 5." when rating is too high', () => {
    // given
    const control = component.chefForm.get('rating');
    control?.enable();
    control?.setValue(6);
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('Rating cannot be more than 5.')).toBeTrue();
  });

  it('should show "CNP is required." when CNP is empty', () => {
    // given
    const control = component.chefForm.get('cnp');
    control?.enable();
    control?.setValue('');
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('CNP is required.')).toBeTrue();
  });

  it('should show "CNP must contain only digits." when CNP is invalid', () => {
    // given
    const control = component.chefForm.get('cnp');
    control?.enable();
    control?.setValue('abc');
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('CNP must contain only digits.')).toBeTrue();
  });

  it('should show "Birth date is required." when birthDate is empty', () => {
    // given
    const control = component.chefForm.get('birthDate');
    control?.enable();
    control?.setValue('');
    control?.markAsTouched();

    // when
    fixture.detectChanges();

    // then
    expect(hasTextDangerContaining('Birth date is required.')).toBeTrue();
  });

  it('should show delete button only for ADMIN', () => {
    // given
    component.loggedUser = mockAdminUser;

    // when
    fixture.detectChanges();

    // then
    const btn = fixture.nativeElement.querySelector('button.btn-outline-danger');
    expect(btn).toBeTruthy();
  });

  it('should show edit button for MODERATOR', () => {
    // given
    component.loggedUser = mockModeratorUser;

    // when
    fixture.detectChanges();

    // then
    const btn = fixture.nativeElement.querySelector('button.btn-outline-primary');
    expect(btn).toBeTruthy();
  });

  it('should disable edit button if form is invalid', () => {
    // given
    component.editMode = true;
    const control = component.chefForm.get('name');
    control?.enable();
    control?.setValue('');
    component.chefForm.markAllAsTouched();
    fixture.detectChanges();

    // when
    const btn = fixture.nativeElement.querySelector('button.btn-outline-primary');

    // then
    expect(btn.disabled).toBeTrue();
  });

  it('should show edit icon when editMode is false', () => {
    // given
    component.editMode = false;

    // when
    fixture.detectChanges();
    const icon = fixture.nativeElement.querySelector('button.btn-outline-primary i.fas');

    // then
    expect(icon?.classList).toContain('fa-pen');
  });

  it('should show save icon when editMode is true', () => {
    // given
    component.editMode = true;

    // when
    fixture.detectChanges();
    const icon = fixture.nativeElement.querySelector('button.btn-outline-primary i.fas');

    // then
    expect(icon?.classList).toContain('fa-save');
  });
});
