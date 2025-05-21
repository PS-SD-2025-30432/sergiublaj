import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { ChefComponent } from './chef.component';
import { ChefService } from '../../../core/services/chef/chef.service';
import { AuthService } from '../../../core/services/auth/auth.service';
import { ModalService } from '../../../core/services/modal/modal.service';
import { mockChef, mockAdminUser, mockModeratorUser } from './chef.component.spec.data';

describe('ChefComponent', () => {
  let component: ChefComponent;
  let fixture: ComponentFixture<ChefComponent>;
  let chefServiceSpy: jasmine.SpyObj<ChefService>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let modalServiceSpy: jasmine.SpyObj<ModalService>;

  beforeEach(async () => {
    chefServiceSpy = jasmine.createSpyObj('ChefService', ['getById', 'update', 'delete']);
    authServiceSpy  = jasmine.createSpyObj('AuthService', [], { user$: of(mockAdminUser) });
    modalServiceSpy = jasmine.createSpyObj('ModalService', ['open'], { confirm$: of(true) });

    await TestBed.configureTestingModule({
      imports: [
        ChefComponent,
        HttpClientTestingModule,
        ReactiveFormsModule
      ],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: convertToParamMap({ id: '1' }) } }
        },
        { provide: ChefService, useValue: chefServiceSpy },
        { provide: AuthService, useValue: authServiceSpy },
        { provide: ModalService, useValue: modalServiceSpy }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture  = TestBed.createComponent(ChefComponent);
    component = fixture.componentInstance;
  });

  function hasTextDangerContaining(text: string): boolean {
    const elements = fixture.nativeElement.querySelectorAll('.text-danger');

    return Array.from(elements).some(element =>
      (element as HTMLElement).textContent?.includes(text)
    );
  }

  describe('when loading the chef details', () => {
    beforeEach(() => {
      // given: getById returns a mock chef
      chefServiceSpy.getById.and.returnValue(of(mockChef));

      // when: component initializes
      fixture.detectChanges();
    });

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
      const messageElement = fixture.nativeElement.querySelector('.alert-info');
      expect(messageElement?.textContent).toContain('No chef selected.');
    });

    it('should show "Name is required." when name is touched and empty', () => {
      // given
      const nameControl = component.chefForm.get('name')!;
      nameControl.enable();
      nameControl.setValue('');
      nameControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('Name is required.')).toBeTrue();
    });

    it('should show "Rating is required." when rating is empty', () => {
      // given
      const ratingControl = component.chefForm.get('rating')!;
      ratingControl.enable();
      ratingControl.setValue(null);
      ratingControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('Rating is required.')).toBeTrue();
    });

    it('should show "Rating must be at least 0." when rating is negative', () => {
      // given
      const ratingControl = component.chefForm.get('rating')!;
      ratingControl.enable();
      ratingControl.setValue(-1);
      ratingControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('Rating must be at least 0.')).toBeTrue();
    });

    it('should show "Rating cannot be more than 5." when rating is too high', () => {
      // given
      const ratingControl = component.chefForm.get('rating')!;
      ratingControl.enable();
      ratingControl.setValue(6);
      ratingControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('Rating cannot be more than 5.')).toBeTrue();
    });

    it('should show "CNP is required." when CNP is empty', () => {
      // given
      const cnpControl = component.chefForm.get('cnp')!;
      cnpControl.enable();
      cnpControl.setValue('');
      cnpControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('CNP is required.')).toBeTrue();
    });

    it('should show "CNP must contain only digits." when CNP is invalid', () => {
      // given
      const cnpControl = component.chefForm.get('cnp')!;
      cnpControl.enable();
      cnpControl.setValue('abc');
      cnpControl.markAsTouched();

      // when
      fixture.detectChanges();

      // then
      expect(hasTextDangerContaining('CNP must contain only digits.')).toBeTrue();
    });

    it('should show "Birth date is required." when birthDate is empty', () => {
      // given
      const birthDateControl = component.chefForm.get('birthDate')!;
      birthDateControl.enable();
      birthDateControl.setValue('');
      birthDateControl.markAsTouched();

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
      const deleteButtonElement = fixture.nativeElement.querySelector('button.btn-outline-danger');
      expect(deleteButtonElement).toBeTruthy();
    });

    it('should show edit button for MODERATOR', () => {
      // given
      component.loggedUser = mockModeratorUser;

      // when
      fixture.detectChanges();

      // then
      const editButtonElement = fixture.nativeElement.querySelector('button.btn-outline-primary');
      expect(editButtonElement).toBeTruthy();
    });

    it('should disable edit button if form is invalid', () => {
      // given
      component.editMode = true;
      const nameControl = component.chefForm.get('name')!;
      nameControl.enable();
      nameControl.setValue('');
      component.chefForm.markAllAsTouched();

      // when
      fixture.detectChanges();

      // then
      const editButtonElement = fixture.nativeElement.querySelector('button.btn-outline-primary');
      expect(editButtonElement.disabled).toBeTrue();
    });

    it('should show edit icon when editMode is false', () => {
      // given
      component.editMode = false;

      // when
      fixture.detectChanges();

      // then
      const iconElement = fixture.nativeElement.querySelector('button.btn-outline-primary i.fas');
      expect(iconElement?.classList).toContain('fa-pen');
    });

    it('should show save icon when editMode is true', () => {
      // given
      component.editMode = true;

      // when
      fixture.detectChanges();

      // then
      const iconElement = fixture.nativeElement.querySelector('button.btn-outline-primary i.fas');
      expect(iconElement?.classList).toContain('fa-save');
    });
  });

  describe('when saving changes', () => {
    beforeEach(() => {
      // given: stub getById and update for this block
      chefServiceSpy.getById.and.returnValue(of(mockChef));
      chefServiceSpy.update.and.returnValue(of(mockChef));

      // when: initialize component and switch to edit mode
      fixture.detectChanges();
      component.editMode = true;
      fixture.detectChanges();
    });

    it('should call update on save when form is valid', () => {
      // given
      const nameControl = component.chefForm.get('name')!;
      nameControl.setValue('New Chef Name');
      component.chefForm.markAllAsTouched();
      fixture.detectChanges();

      // when
      const saveButtonElement = fixture.debugElement.query(By.css('button.btn-outline-primary'));
      saveButtonElement.triggerEventHandler('click', null);

      // then
      expect(chefServiceSpy.update).toHaveBeenCalledWith(
        jasmine.anything(),
        jasmine.objectContaining({ name: 'New Chef Name' })
      );
    });

    describe('when deleting the chef', () => {
      beforeEach(() => {
        // given: stub getById to populate form and stub delete
        chefServiceSpy.getById.and.returnValue(of(mockChef));
        chefServiceSpy.delete.and.returnValue(of());

        // when: initialize component and set user as ADMIN
        fixture.detectChanges();
        component.loggedUser = mockAdminUser;
        fixture.detectChanges();
      });

      it('should open confirmation modal and call delete when confirmed', () => {
        // when
        const deleteButtonElement = fixture.debugElement.query(By.css('button.btn-outline-danger'));
        deleteButtonElement.triggerEventHandler('click', null);

        // then
        expect(modalServiceSpy.open).toHaveBeenCalled();
        expect(chefServiceSpy.delete).toHaveBeenCalledWith(mockChef.id);
      });
    });
  });
});
