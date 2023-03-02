import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AvailablePollsComponent} from './available-polls.component';

describe('AvailablePollsComponent', () => {
  let component: AvailablePollsComponent;
  let fixture: ComponentFixture<AvailablePollsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AvailablePollsComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AvailablePollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
