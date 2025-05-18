import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ChefsComponent } from './chefs.component';

describe('ChefsComponent', () => {
  let component: ChefsComponent;
  let fixture: ComponentFixture<ChefsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChefsComponent, HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              queryParams: {},
              paramMap: convertToParamMap({}),
              params: {}
            },
            paramMap: of(convertToParamMap({})),
            queryParams: of({}),
            queryParamMap: of(convertToParamMap({ searchBy: '', page: '0' })) // ✅ this is the fix
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ChefsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
