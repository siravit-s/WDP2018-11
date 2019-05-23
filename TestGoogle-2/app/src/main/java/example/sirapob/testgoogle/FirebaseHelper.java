package example.sirapob.testgoogle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Activities> activities = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Activities> activities, List<String> keys);
        void DataIsinserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("activities");
    }

    public void readActivities(final DataStatus dataStatus) {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activities.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(0, keyNode.getKey());
                    Activities activity = keyNode.getValue(Activities.class);
                    activities.add(0, activity);
                }
                dataStatus.DataIsLoaded(activities,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
