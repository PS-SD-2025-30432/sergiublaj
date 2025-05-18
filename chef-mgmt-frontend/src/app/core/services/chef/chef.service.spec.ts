import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ChefService } from './chef.service';
import { ROUTES } from '../../config/routes.enum';
import { ChefFilter } from '../../../feature/chefs/models/chef-filter.model';
import {
  mockChefRequest,
  mockChefResponse,
  mockChefCollectionResponse
} from './chef.service.spec.data';

describe('ChefService', () => {
  let service: ChefService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ChefService]
    });

    service = TestBed.inject(ChefService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all chefs', () => {
    const filter: ChefFilter = { name: 'Gordon', pageNumber: 0, pageSize: 10 };

    service.getAll(filter).subscribe(response => {
      expect(response).toEqual(mockChefCollectionResponse);
    });

    const req = httpMock.expectOne(r =>
      r.url === `/v1/${ROUTES.CHEFS}` && r.method === 'GET'
    );
    expect(req.request.params.has('name')).toBeTrue();
    req.flush(mockChefCollectionResponse);
  });

  it('should get chef by ID', () => {
    const chefId = '123';

    service.getById(chefId).subscribe(response => {
      expect(response).toEqual(mockChefResponse);
    });

    const req = httpMock.expectOne(`/v1/${ROUTES.CHEFS}/${chefId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockChefResponse);
  });

  it('should save a chef', () => {
    service.save(mockChefRequest).subscribe(response => {
      expect(response).toEqual(mockChefResponse);
    });

    const req = httpMock.expectOne(`/v1/${ROUTES.CHEFS}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockChefRequest);
    req.flush(mockChefResponse);
  });

  it('should update a chef', () => {
    const chefId = '123';

    service.update(chefId, mockChefRequest).subscribe(response => {
      expect(response).toEqual(mockChefResponse);
    });

    const req = httpMock.expectOne(`/v1/${ROUTES.CHEFS}/${chefId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockChefRequest);
    req.flush(mockChefResponse);
  });

  it('should delete a chef', () => {
    const chefId = '123';

    service.delete(chefId).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`/v1/${ROUTES.CHEFS}/${chefId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
