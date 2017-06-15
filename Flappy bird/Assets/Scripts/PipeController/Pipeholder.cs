using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Pipeholder : MonoBehaviour {

    public float speed;
	
	// Update is called once per frame
	void Update () {
        if (BirdController.instance != null) {
            if(BirdController.instance.flag == 1)
            {
                Destroy(GetComponent<Pipeholder>());
            }
        }
        _PipeMovement();
	}

    void _PipeMovement()
    {
        Vector3 temp = transform.position;
        temp.x -= speed * Time.deltaTime;
        transform.position = temp;
    }

    void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Destroy")
        {
            Destroy(gameObject);
        }
    }
}
